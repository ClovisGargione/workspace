package filter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public class AuthFilter implements Filter {


    private static final String AUTH_ERROR_MSG = "Please make sure your request has an Authorization header",
            EXPIRE_ERROR_MSG = "Token has expired",
            JWT_ERROR_MSG = "Unable to parse JWT",
            JWT_INVALID_MSG = "Invalid JWT token";
    private static final String INVALIDTOKEN_ERROR_MSG = "Token inválido";

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authHeader = httpRequest.getHeader(AuthUtils.AUTH_HEADER_KEY);

        if (StringUtils.isBlank(authHeader)
                || authHeader.split(" ").length != 2) {

            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    AUTH_ERROR_MSG);
        } else {
            JWTClaimsSet claimSet = null;
            try {
                claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
            } catch (ParseException e) {
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        JWT_ERROR_MSG);
            } catch (JOSEException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            // ensure that the token is not expired
            // verificando se o token expirou
            if (AuthUtils.tokenExpirado(claimSet)) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, EXPIRE_ERROR_MSG);
            }

            if(!AuthUtils.palavraSecretaValida(authHeader)) {
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, INVALIDTOKEN_ERROR_MSG);
            }


            if(claimSet.getCustomClaim("login") == null){
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, INVALIDTOKEN_ERROR_MSG);
            }

            if(claimSet.getCustomClaim("perfil") == null){
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, INVALIDTOKEN_ERROR_MSG);
            }

            chain.doFilter(request, response);

        }

    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }
}
