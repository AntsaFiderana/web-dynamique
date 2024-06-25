package mg.controller;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.File;
import java.util.List;
import java.lang.Class;
import java.net.URL;
import java.util.Enumeration;
import outils.*;


public class FrontController extends HttpServlet{

    ArrayList <String>mescontrolleurs=new ArrayList<>();
    boolean checked=false;
   
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String controllerpackagename=context.getInitParameter("controllers");
        PrintWriter writer=response.getWriter();
        if(!checked)
        {
            try {
                List<Class<?>> classes=Outil.getClasses(controllerpackagename);
                for (Class<?> class1 : classes) {
                    if(class1.isAnnotationPresent(annotations.AnnotationController.class))
                    {
                        //System.out.println(class1.getName());
                        mescontrolleurs.add(class1.getName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(writer);
            }
            checked=true;
        }
        writer.println("Voici mes controlleurs");
        for (String controlleur : mescontrolleurs) {
            writer.println(controlleur);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    


    
}