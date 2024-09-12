import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('VariableNotification e2e test', () => {
  const variableNotificationPageUrl = '/variable-notification';
  const variableNotificationPageUrlPattern = new RegExp('/variable-notification(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const variableNotificationSample = {};

  let variableNotification;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/variable-notifications+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/variable-notifications').as('postEntityRequest');
    cy.intercept('DELETE', '/api/variable-notifications/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (variableNotification) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/variable-notifications/${variableNotification.idVarNotification}`,
      }).then(() => {
        variableNotification = undefined;
      });
    }
  });

  it('VariableNotifications menu should load VariableNotifications page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('variable-notification');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('VariableNotification').should('exist');
    cy.url().should('match', variableNotificationPageUrlPattern);
  });

  describe('VariableNotification page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(variableNotificationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create VariableNotification page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/variable-notification/new$'));
        cy.getEntityCreateUpdateHeading('VariableNotification');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', variableNotificationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/variable-notifications',
          body: variableNotificationSample,
        }).then(({ body }) => {
          variableNotification = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/variable-notifications+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/variable-notifications?page=0&size=20>; rel="last",<http://localhost/api/variable-notifications?page=0&size=20>; rel="first"',
              },
              body: [variableNotification],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(variableNotificationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details VariableNotification page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('variableNotification');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', variableNotificationPageUrlPattern);
      });

      it('edit button click should load edit VariableNotification page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('VariableNotification');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', variableNotificationPageUrlPattern);
      });

      it('edit button click should load edit VariableNotification page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('VariableNotification');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', variableNotificationPageUrlPattern);
      });

      it('last delete button click should delete instance of VariableNotification', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('variableNotification').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', variableNotificationPageUrlPattern);

        variableNotification = undefined;
      });
    });
  });

  describe('new VariableNotification page', () => {
    beforeEach(() => {
      cy.visit(`${variableNotificationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('VariableNotification');
    });

    it('should create an instance of VariableNotification', () => {
      cy.get(`[data-cy="idVarNotification"]`).type('0cbe9f67-2cdd-4b8f-8d47-1c443c235a7b');
      cy.get(`[data-cy="idVarNotification"]`).should('have.value', '0cbe9f67-2cdd-4b8f-8d47-1c443c235a7b');

      cy.get(`[data-cy="codeVariable"]`).type('mériter vu que');
      cy.get(`[data-cy="codeVariable"]`).should('have.value', 'mériter vu que');

      cy.get(`[data-cy="description"]`).type('rejoindre près de');
      cy.get(`[data-cy="description"]`).should('have.value', 'rejoindre près de');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        variableNotification = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', variableNotificationPageUrlPattern);
    });
  });
});
