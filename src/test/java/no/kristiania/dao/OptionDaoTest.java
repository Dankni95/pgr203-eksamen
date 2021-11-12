package no.kristiania.dao;

import no.kristiania.entity.Option;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;


class OptionDaoTest {
    private final OptionDao dao = new OptionDao(TestData.testDataSource());

    @Test
    void shouldListSavedOptions() throws SQLException {
        String option1 = "option-" + UUID.randomUUID();
        String option2 = "option-" + UUID.randomUUID();

        Option option = new Option();
        option.setTitle(option1);
        option.setQuestionId(1);
        dao.save(option);

        Option op = new Option();
        op.setTitle(option2);
        dao.save(op);

        assertThat(dao.listAll())
                .extracting(Option::getTitle)
                .contains(option.getTitle(), op.getTitle());
    }
}