package com.publisher.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.publisher.SearchEngine;
import com.publisher.utils.OperateXMLByDOM;
import com.publisher.utils.XSLTTransformer;
import com.publisher.xmlparsers.CombSearchDocBuilder;

public class FullTextSearch extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("application/xml; charset=utf-8");
    	PrintWriter out = response.getWriter();
    	Document xml = null;
    	try {
			SearchEngine searchEngine = new SearchEngine();
			//TODO 中文编码问题尚未解决，亲测chrome是可以用的。
			String keyword = new String(request.getParameter("key").getBytes("ISO8859-1"),"UTF-8");
			//out.print("<h>"+keyword+"</h>");
			//System.out.println(keyword+'\n');
			String dmcs = searchEngine.fullTextSearch((keyword != null)?keyword:"发动机");
			CombSearchDocBuilder docBuilder = new CombSearchDocBuilder();
			xml = docBuilder.createTreeViewDoc(dmcs);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	//XSLTTransformer.xsl2Stream("", out, "");
    	out.print(OperateXMLByDOM.doc2FormatString(xml));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
