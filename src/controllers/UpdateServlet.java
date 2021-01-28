package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.task;
import utils.DBUtil;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em=DBUtil.createEntityManager();

        task m =em.find(task.class, (Integer)(request.getSession().getAttribute("task_id")));

        //フォームに上書き
        String content=request.getParameter("content");
        m.setContent(content);

        Timestamp currentTime=new Timestamp(System.currentTimeMillis());
        m.setUpdated_at(currentTime);

        //データベース更新
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        //セッションスコープ上の不要になったデータを削除
        request.getSession().removeAttribute("task_id");

        //indexページへとぶ
        response.sendRedirect(request.getContextPath()+"/index");


    }

}
