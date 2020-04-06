package com.aagu.blog;

import com.aagu.blog.Dao.CommentDao;
import com.aagu.blog.Dao.LogDao;
import com.aagu.blog.Models.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

	@Autowired
	CommentDao commentDao;

	@Autowired
	LogDao logDao;

	@Test
	public void getArticleByMonth() {
		List<Comment> comments = commentDao.getByPage(0, 5, "我", "desc", "unread");
		for (Comment c : comments) {
			System.out.println(c.toString());
		}
	}

//	@Test
//	public void log() {
//		logDao.appendLog("test log", 1);
//		List<Log> logs = logDao.getLog(new HashMap<>());
//		System.out.println(logs.size());;
//	}

}
