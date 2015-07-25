/**
 * 
 */
package filter;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import jpa.JpaUtil;

import org.apache.log4j.Logger;

/**
 * @author c_r_s_000
 *
 */
public class ConnectionFilter implements Filter {

	private final static Logger logger = Logger.getLogger(ConnectionFilter.class);

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		EntityManager entityManager = JpaUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			chain.doFilter(request, response);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Erro: ",e);
			e.printStackTrace();
			if(entityManager.isOpen()){
				entityManager.getTransaction().rollback();
			}
		}
		finally {
			if(entityManager.isOpen()){
				entityManager.close();
			}
		}
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	



}
