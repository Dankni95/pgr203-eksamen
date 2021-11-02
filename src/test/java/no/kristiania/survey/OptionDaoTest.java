package no.kristiania.survey;

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

        dao.save(option1);
        dao.save(option2);

        assertThat(dao.listAll())
                .contains(option1, option2);
    }
}