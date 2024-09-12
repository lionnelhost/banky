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

describe('MessageErreur e2e test', () => {
  const messageErreurPageUrl = '/message-erreur';
  const messageErreurPageUrlPattern = new RegExp('/message-erreur(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const messageErreurSample = {};

  let messageErreur;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/message-erreurs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/message-erreurs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/message-erreurs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (messageErreur) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/message-erreurs/${messageErreur.idMessageErreur}`,
      }).then(() => {
        messageErreur = undefined;
      });
    }
  });

  it('MessageErreurs menu should load MessageErreurs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('message-erreur');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MessageErreur').should('exist');
    cy.url().should('match', messageErreurPageUrlPattern);
  });

  describe('MessageErreur page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(messageErreurPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MessageErreur page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/message-erreur/new$'));
        cy.getEntityCreateUpdateHeading('MessageErreur');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', messageErreurPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/message-erreurs',
          body: messageErreurSample,
        }).then(({ body }) => {
          messageErreur = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/message-erreurs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/message-erreurs?page=0&size=20>; rel="last",<http://localhost/api/message-erreurs?page=0&size=20>; rel="first"',
              },
              body: [messageErreur],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(messageErreurPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MessageErreur page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('messageErreur');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', messageErreurPageUrlPattern);
      });

      it('edit button click should load edit MessageErreur page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MessageErreur');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', messageErreurPageUrlPattern);
      });

      it('edit button click should load edit MessageErreur page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MessageErreur');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', messageErreurPageUrlPattern);
      });

      it('last delete button click should delete instance of MessageErreur', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('messageErreur').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', messageErreurPageUrlPattern);

        messageErreur = undefined;
      });
    });
  });

  describe('new MessageErreur page', () => {
    beforeEach(() => {
      cy.visit(`${messageErreurPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MessageErreur');
    });

    it('should create an instance of MessageErreur', () => {
      cy.get(`[data-cy="idMessageErreur"]`).type('d870021a-d2e2-494d-b914-6db49a60890a');
      cy.get(`[data-cy="idMessageErreur"]`).should('have.value', 'd870021a-d2e2-494d-b914-6db49a60890a');

      cy.get(`[data-cy="codeErreur"]`).type('délégation');
      cy.get(`[data-cy="codeErreur"]`).should('have.value', 'délégation');

      cy.get(`[data-cy="description"]`).type('personnel');
      cy.get(`[data-cy="description"]`).should('have.value', 'personnel');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        messageErreur = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', messageErreurPageUrlPattern);
    });
  });
});
