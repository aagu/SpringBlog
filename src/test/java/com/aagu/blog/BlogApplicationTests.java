package com.aagu.blog;

import com.aagu.blog.Dao.ArticleDao;
import com.aagu.blog.Models.Article;
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
	ArticleDao articleDao;

	@Test
	public void getArticleByMonth() {
		List<String> months = articleDao.orderByMonth();
		System.out.println(months.get(0));
	}

}
