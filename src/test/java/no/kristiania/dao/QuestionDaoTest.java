package no.kristiania.dao;

import no.kristiania.entity.Question;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionDaoTest  {
    private final QuestionDao dao = new QuestionDao(TestData.testDataSource());

    @Test
    void shouldRetrieveSavedQuestion() throws SQLException {
        Question question = exampleQuestion();
        dao.save(question);
        assertThat(dao.retrieve(question.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(question);
    }

    @Test
    void shouldListAllQuestions() throws SQLException {
        Question question = exampleQuestion();
        dao.save(question);
        Question anotherQuestion = exampleQuestion();
        dao.save(anotherQuestion);

        assertThat(dao.listAll())
                .extracting(Question::getId)
                .contains(question.getId(), anotherQuestion.getId());
    }

    public static Question exampleQuestion() {
        Question question = new Question();
        question.setTitle(TestData.pickOne("Frukt", "Farge", "Bil", "Film", "Godteri"));
        question.setText(TestData.pickOne("Hva er din favoritt av følgende alternativer", "Hvilken liker du best av disse", "Velg ett av alternativene"));
        return question;
    }
}