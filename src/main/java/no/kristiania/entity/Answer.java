package no.kristiania.entity;

public class Answer {
    private long id;
    private long userSurveyId;
    private long questionId;
    private String optionTitle;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserSurveyId() {
        return userSurveyId;
    }

    public void setUserSurveyId(long userSurveyId) {
        this.userSurveyId = userSurveyId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }
}
