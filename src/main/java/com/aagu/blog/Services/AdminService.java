package com.aagu.blog.Services;

import com.aagu.blog.Models.Article;
import com.aagu.blog.Models.Label;
import com.aagu.blog.Views.AdminVO;

import java.util.List;

public interface AdminService {

    AdminVO getMainAdminPage(Integer page);

    List<Label> getAllFinalLabels();

    List<Article> getAllArticles();
}
