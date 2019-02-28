package finalprojectNew.business.request;


import finalprojectNew.util.Role;
import finalprojectNew.util.URLMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Configuration
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException { handle(request, response, authentication);
            clearAuthenticationAttributes(request);
    }

    protected void handle (HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException { String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Response has been committed. Unable to redirect to  " + targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request,response,targetUrl);
    }

    /**
     * Builds the target URL according to the logic defined in the main class
     * Javadoc.
     */

    private String determineTargetUrl(Authentication authentication) {
        boolean isCandidate = false;
        boolean isEmployer = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities){
            if (Role.candidate.name().equalsIgnoreCase(grantedAuthority.getAuthority())){
                isCandidate = true;
                break;
            } else if (Role.employer.name().equalsIgnoreCase(grantedAuthority.getAuthority())){
                isEmployer = true;
                break;
            }
        }
        if (isCandidate) {
            return URLMapper.CANDIDATE_JOBS;
        } else if (isEmployer) {
            return URLMapper.EMPLOYER_JOB_LISTING_URL;
        } else {
            throw new IllegalStateException();
        }
    }

    protected void clearAuthenticationAttributes (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session==null){
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}