package sn.modeltech.banky.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageErreurAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMessageErreurAllPropertiesEquals(MessageErreur expected, MessageErreur actual) {
        assertMessageErreurAutoGeneratedPropertiesEquals(expected, actual);
        assertMessageErreurAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMessageErreurAllUpdatablePropertiesEquals(MessageErreur expected, MessageErreur actual) {
        assertMessageErreurUpdatableFieldsEquals(expected, actual);
        assertMessageErreurUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMessageErreurAutoGeneratedPropertiesEquals(MessageErreur expected, MessageErreur actual) {
        // empty method
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMessageErreurUpdatableFieldsEquals(MessageErreur expected, MessageErreur actual) {
        assertThat(expected)
            .as("Verify MessageErreur relevant properties")
            .satisfies(e -> assertThat(e.getIdMessageErreur()).as("check idMessageErreur").isEqualTo(actual.getIdMessageErreur()))
            .satisfies(e -> assertThat(e.getCodeErreur()).as("check codeErreur").isEqualTo(actual.getCodeErreur()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMessageErreurUpdatableRelationshipsEquals(MessageErreur expected, MessageErreur actual) {
        // empty method
    }
}
