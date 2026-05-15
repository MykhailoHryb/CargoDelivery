package com.final_project.cargo_delivery.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Custom jsp tag sets title op application with additional title
 *
 * @author Mykhailo Hryb
 */
public class TitleTag extends TagSupport {

    private String pageTitle;

    private String locale;

    @Override
    public int doStartTag() throws JspException {
        try {
            ResourceBundle messages;
            if (locale == null) {
                messages = ResourceBundle.getBundle("messages",
                        new Locale("en_EN"));
            } else {
                messages = ResourceBundle.getBundle("messages",
                        new Locale(locale));
            }
            String prefix = messages.getString("main.title_short");

            if (pageTitle != null) {
                pageContext.getOut().write(prefix + ", " + pageTitle);
            } else {
                pageContext.getOut().write(prefix);
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public void release() {
        super.release();
        this.pageTitle = null;
        this.locale = null;

    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }


    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}

