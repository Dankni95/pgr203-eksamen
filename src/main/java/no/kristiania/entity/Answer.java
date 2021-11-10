package no.kristiania.entity;

public class Answer {
    private long id;
    private long user_survey_id;
    private long question_id;
    private long option_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_survey_id() {
        return user_survey_id;
    }

    public void setUser_survey_id(long user_survey_id) {
        this.user_survey_id = user_survey_id;
    }

    public long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }

    public long getOption_id() {
        return option_id;
    }

    public void setOption_id(long option_id) {
        this.option_id = option_id;
    }
}
