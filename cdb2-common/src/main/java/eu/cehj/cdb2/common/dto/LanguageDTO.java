package eu.cehj.cdb2.common.dto;

import eu.cehj.cdb2.entity.Language;

public class LanguageDTO extends BaseDTO{

    private static final long serialVersionUID = -6076737767640901870L;

    private String code;
    private String language;

    public LanguageDTO() {
        super();
    }

    public LanguageDTO(final Language language ) {
        super(language);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }


}
