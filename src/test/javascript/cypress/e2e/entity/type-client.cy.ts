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

describe('TypeClient e2e test', () => {
  const typeClientPageUrl = '/type-client';
  const typeClientPageUrlPattern = new RegExp('/type-client(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const typeClientSample = {};

  let typeClient;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/type-clients+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/type-clients').as('postEntityRequest');
    cy.intercept('DELETE', '/api/type-clients/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (typeClient) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/type-clients/${typeClient.idTypeClient}`,
      }).then(() => {
        typeClient = undefined;
      });
    }
  });

  it('TypeClients menu should load TypeClients page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('type-client');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TypeClient').should('exist');
    cy.url().should('match', typeClientPageUrlPattern);
  });

  describe('TypeClient page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(typeClientPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TypeClient page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/type-client/new$'));
        cy.getEntityCreateUpdateHeading('TypeClient');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeClientPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/type-clients',
          body: typeClientSample,
        }).then(({ body }) => {
          typeClient = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/type-clients+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/type-clients?page=0&size=20>; rel="last",<http://localhost/api/type-clients?page=0&size=20>; rel="first"',
              },
              body: [typeClient],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(typeClientPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TypeClient page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('typeClient');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeClientPageUrlPattern);
      });

      it('edit button click should load edit TypeClient page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeClient');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeClientPageUrlPattern);
      });

      it('edit button click should load edit TypeClient page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeClient');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeClientPageUrlPattern);
      });

      it('last delete button click should delete instance of TypeClient', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('typeClient').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeClientPageUrlPattern);

        typeClient = undefined;
      });
    });
  });

  describe('new TypeClient page', () => {
    beforeEach(() => {
      cy.visit(`${typeClientPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TypeClient');
    });

    it('should create an instance of TypeClient', () => {
      cy.get(`[data-cy="idTypeClient"]`).type('1ee8eadc-93c5-4aa9-9f78-52862b5d78f6');
      cy.get(`[data-cy="idTypeClient"]`).should('have.value', '1ee8eadc-93c5-4aa9-9f78-52862b5d78f6');

      cy.get(`[data-cy="libelle"]`).type('parlementaire auprès de partenaire');
      cy.get(`[data-cy="libelle"]`).should('have.value', 'parlementaire auprès de partenaire');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        typeClient = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', typeClientPageUrlPattern);
    });
  });
});
