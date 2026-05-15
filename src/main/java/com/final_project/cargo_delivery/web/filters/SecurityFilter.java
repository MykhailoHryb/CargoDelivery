package com.final_project.cargo_delivery.web.filters;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.Role;
import com.final_project.cargo_delivery.service.implementation.LocaleServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter. Sets command that available for different roles
 *
 * @author Mykhailo Hryb
 */
@WebFilter(urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);
    private static final Map<Integer, List<String>> accessMap = new HashMap<>();
    private static final List<String> commons = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Setting pages for roles
        accessMap.put(Role.MANAGER.getID(), new ArrayList<>(
                Arrays.asList(
                        "/manager-page",
                        "/profile-page",
                        "/make-order",
                        "/create-receipt-payment",
                        "/change-order-status",
                        "/make-report"
                )
        ));
        accessMap.put(Role.AUTHORIZED.getID(), new ArrayList<>(
                Arrays.asList(
                        "/profile-page",
                        "/make-order",
                        "/change-order-status"
                )
        ));

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        LocaleApplication localeApplication;
        LocaleService localeService = new LocaleServiceImpl();
        Cookie[] arrCookies = httpServletRequest.getCookies();
        localeApplication = localeService.getLocaleByCookieOrDefault(arrCookies);
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        if (accessAllowed(servletRequest)) {
            LOGGER.debug("Filter finished");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String errorMessage = messages.getString("exception.error.dont_have_permission");

            servletRequest.setAttribute("errorMessage", errorMessage);
            LOGGER.trace("Set the request attribute: errorMessage --> " + errorMessage);

            servletRequest.getRequestDispatcher("error_page")
                    .forward(servletRequest, servletResponse);
        }
    }

    /**
     * @param request
     * @return boolean if access for user is allowed
     */
    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getRequestURI() == null || httpRequest.getRequestURI().isEmpty())
            return false;

        HttpSession session = httpRequest.getSession();
        UserViewDto user = (UserViewDto) session.getAttribute("user");
        if (user != null) {
            int userRoleId = user.getRoleId();
            if (userRoleId != 0) {
                return accessMap.get(userRoleId).contains(httpRequest.getRequestURI())
                        || commons.contains(httpRequest.getRequestURI())
                        || accessMap.values().stream().noneMatch(links -> links.contains(httpRequest.getRequestURI()));
            }
        } else {

            //Возвращаем true, если путь не для авторизованных пользователей а для всех
            return accessMap.values().stream().noneMatch(links -> links.contains(httpRequest.getRequestURI()));
        }
        return true;
    }

    @Override
    public void destroy() {
    }
}
