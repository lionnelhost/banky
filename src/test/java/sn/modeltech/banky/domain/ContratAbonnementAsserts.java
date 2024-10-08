package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ContratAbonnementAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContratAbonnementAllPropertiesEquals(ContratAbonnement expected, ContratAbonnement actual) {
        assertContratAbonnementAutoGeneratedPropertiesEquals(expected, actual);
        assertContratAbonnementAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContratAbonnementAllUpdatablePropertiesEquals(ContratAbonnement expected, ContratAbonnement actual) {
        assertContratAbonnementUpdatableFieldsEquals(expected, actual);
        assertContratAbonnementUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContratAbonnementAutoGeneratedPropertiesEquals(ContratAbonnement expected, ContratAbonnement actual) {
        assertThat(expected)
            .as("Verify ContratAbonnement auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContratAbonnementUpdatableFieldsEquals(ContratAbonnement expected, ContratAbonnement actual) {
        assertThat(expected)
            .as("Verify ContratAbonnement relevant properties")
            .satisfies(e -> assertThat(e.getIdContrat()).as("check idContrat").isEqualTo(actual.getIdContrat()))
            .satisfies(e -> assertThat(e.getIdAbonne()).as("check idAbonne").isEqualTo(actual.getIdAbonne()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContratAbonnementUpdatableRelationshipsEquals(ContratAbonnement expected, ContratAbonnement actual) {
        assertThat(expected)
            .as("Verify ContratAbonnement relationships")
            .satisfies(e -> assertThat(e.getContrat()).as("check contrat").isEqualTo(actual.getContrat()))
            .satisfies(e -> assertThat(e.getAbonne()).as("check abonne").isEqualTo(actual.getAbonne()));
    }
}
