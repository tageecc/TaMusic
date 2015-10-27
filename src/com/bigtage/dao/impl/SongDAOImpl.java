package com.bigtage.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.bigtage.bean.HistorySong;
import com.bigtage.bean.Song;
import com.bigtage.dao.SongDAO;

@Repository
public class SongDAOImpl implements SongDAO {
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public boolean save(Song song) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		boolean flag = true;
		try {
			tx = session.beginTransaction();
			session.save(song);
			tx.commit();
		} catch (Exception e) {
			flag = false;
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Song findById(int songid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean eixt(Song song) {
		Session session = sessionFactory.getCurrentSession();
		boolean flag = false;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (session
					.createQuery(
							"from Song s where s.src='" + song.getSrc()
									+ "' and s.uid='" + song.getUid() + "'")
					.list().size() > 0) {
				flag = true;
			}
			tx.commit();
		} catch (Exception e) {
			flag = false;
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<Song> getSongs() {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		List<Song> songs = null;
		try {
			tx = session.beginTransaction();
			songs = session.createQuery("from Song").list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return songs;
	}

	@Override
	public List<Song> getSongById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		List<Song> songs = null;
		try {
			tx = session.beginTransaction();
			songs = session.createQuery("from Song where songid='" + id + "'")
					.list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return songs;
	}

	@Override
	public Song getPrevious(int uid) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		Song song = null;
		try {
			tx = session.beginTransaction();
			song = (Song) session
					.createSQLQuery("select s.* from song as s , history_song as h where s.songid=h.songid order by h.time LIMIT 1");
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return song;
	}

	@Override
	public boolean saveHistory(HistorySong historySong) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		boolean flag = true;
		try {
			tx = session.beginTransaction();
			session.save(historySong);
			tx.commit();
		} catch (Exception e) {
			flag = false;
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return flag;

	}

	@Override
	public boolean increaseSong(int songid) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		boolean flag = true;
		try {
			tx = session.beginTransaction();
			session.createSQLQuery("update song as s set s.count=s.count+1 where s.songid='"
					+ songid + "'");
			tx.commit();
		} catch (Exception e) {
			flag = false;
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return flag;
	}
}
