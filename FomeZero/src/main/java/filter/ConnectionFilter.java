package filter;

import jpa.JpaUtil;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.servlet.*;
import java.io.IOException;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public class ConnectionFilter implements Filter {

    private final static Logger logger = Logger.getLogger(ConnectionFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            filterChain.doFilter(servletRequest, servletResponse);
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

    public void destroy() {

    }
}
