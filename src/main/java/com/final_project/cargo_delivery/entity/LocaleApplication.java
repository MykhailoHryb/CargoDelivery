package com.final_project.cargo_delivery.entity;

import java.util.Objects;

/**
 * LocaleApplication
 *
 * @author Mykhailo Hryb
 */
public class LocaleApplication {

    private int idLocale;

    private String fullName;

    private String shortName;

    public int getLocaleId() {
        return idLocale;
    }

    public void setIdLocale(int idLocale) {
        this.idLocale = idLocale;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocaleApplication localeApplication = (LocaleApplication) o;
        return idLocale == localeApplication.idLocale &&
                Objects.equals(fullName, localeApplication.fullName) &&
                shortName.equals(localeApplication.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLocale, fullName, shortName);
    }

    @Override
    public String toString() {
        return "Locale{" +
                "idLocale=" + idLocale +
                ", fullName='" + fullName + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }
}
