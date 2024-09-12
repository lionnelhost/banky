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

describe('ParametrageNotification e2e test', () => {
  const parametrageNotificationPageUrl = '/parametrage-notification';
  const parametrageNotificationPageUrlPattern = new RegExp('/parametrage-notification(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const parametrageNotificationSample = {};

  let parametrageNotification;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/parametrage-notifications+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/parametrage-notifications').as('postEntityRequest');
    cy.intercept('DELETE', '/api/parametrage-notifications/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (parametrageNotification) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/parametrage-notifications/${parametrageNotification.idParamNotif}`,
      }).then(() => {
        parametrageNotification = undefined;
      });
    }
  });

  it('ParametrageNotifications menu should load ParametrageNotifications page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('parametrage-notification');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ParametrageNotification').should('exist');
    cy.url().should('match', parametrageNotificationPageUrlPattern);
  });

  describe('ParametrageNotification page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(parametrageNotificationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ParametrageNotification page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/parametrage-notification/new$'));
        cy.getEntityCreateUpdateHeading('ParametrageNotification');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parametrageNotificationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/parametrage-notifications',
          body: parametrageNotificationSample,
        }).then(({ body }) => {
          parametrageNotification = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/parametrage-notifications+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/parametrage-notifications?page=0&size=20>; rel="last",<http://localhost/api/parametrage-notifications?page=0&size=20>; rel="first"',
              },
              body: [parametrageNotification],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(parametrageNotificationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ParametrageNotification page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('parametrageNotification');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parametrageNotificationPageUrlPattern);
      });

      it('edit button click should load edit ParametrageNotification page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ParametrageNotification');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parametrageNotificationPageUrlPattern);
      });

      it('edit button click should load edit ParametrageNotification page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ParametrageNotification');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parametrageNotificationPageUrlPattern);
      });

      it('last delete button click should delete instance of ParametrageNotification', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('parametrageNotification').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', parametrageNotificationPageUrlPattern);

        parametrageNotification = undefined;
      });
    });
  });

  describe('new ParametrageNotification page', () => {
    beforeEach(() => {
      cy.visit(`${parametrageNotificationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ParametrageNotification');
    });

    it('should create an instance of ParametrageNotification', () => {
      cy.get(`[data-cy="idParamNotif"]`).type('b03d3c88-8e65-42f1-8c3e-2ece503443c1');
      cy.get(`[data-cy="idParamNotif"]`).should('have.value', 'b03d3c88-8e65-42f1-8c3e-2ece503443c1');

      cy.get(`[data-cy="objetNotif"]`).type('mince bof permettre');
      cy.get(`[data-cy="objetNotif"]`).should('have.value', 'mince bof permettre');

      cy.get(`[data-cy="typeNotif"]`).type('équipe de recherche debout à moins de');
      cy.get(`[data-cy="typeNotif"]`).should('have.value', 'équipe de recherche debout à moins de');

      cy.get(`[data-cy="contenu"]`).type('hôte dormir');
      cy.get(`[data-cy="contenu"]`).should('have.value', 'hôte dormir');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        parametrageNotification = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', parametrageNotificationPageUrlPattern);
    });
  });
});
