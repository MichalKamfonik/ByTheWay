package pl.kamfonik.bytheway.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import pl.kamfonik.bytheway.entity.Role;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-tests.properties")
class RoleRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void shouldFindRoleByName() {
        // given
        Role admin = new Role();
        admin.setName("ROLE_ADMIN");

        entityManager.persist(admin);
        entityManager.flush();

        // when
        Role found = roleRepository.findByName(admin.getName());

        // then
        assertThat(found.getName()).isEqualTo(admin.getName());
    }
}