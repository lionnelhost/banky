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

describe('TypeTransaction e2e test', () => {
  const typeTransactionPageUrl = '/type-transaction';
  const typeTransactionPageUrlPattern = new RegExp('/type-transaction(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const typeTransactionSample = {};

  let typeTransaction;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/type-transactions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/type-transactions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/type-transactions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (typeTransaction) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/type-transactions/${typeTransaction.idTypeTransaction}`,
      }).then(() => {
        typeTransaction = undefined;
      });
    }
  });

  it('TypeTransactions menu should load TypeTransactions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('type-transaction');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TypeTransaction').should('exist');
    cy.url().should('match', typeTransactionPageUrlPattern);
  });

  describe('TypeTransaction page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(typeTransactionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TypeTransaction page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/type-transaction/new$'));
        cy.getEntityCreateUpdateHeading('TypeTransaction');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeTransactionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/type-transactions',
          body: typeTransactionSample,
        }).then(({ body }) => {
          typeTransaction = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/type-transactions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/type-transactions?page=0&size=20>; rel="last",<http://localhost/api/type-transactions?page=0&size=20>; rel="first"',
              },
              body: [typeTransaction],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(typeTransactionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TypeTransaction page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('typeTransaction');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeTransactionPageUrlPattern);
      });

      it('edit button click should load edit TypeTransaction page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeTransaction');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeTransactionPageUrlPattern);
      });

      it('edit button click should load edit TypeTransaction page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TypeTransaction');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeTransactionPageUrlPattern);
      });

      it('last delete button click should delete instance of TypeTransaction', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('typeTransaction').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', typeTransactionPageUrlPattern);

        typeTransaction = undefined;
      });
    });
  });

  describe('new TypeTransaction page', () => {
    beforeEach(() => {
      cy.visit(`${typeTransactionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TypeTransaction');
    });

    it('should create an instance of TypeTransaction', () => {
      cy.get(`[data-cy="idTypeTransaction"]`).type('86f6eda0-1e31-4511-81e8-b26311d4d632');
      cy.get(`[data-cy="idTypeTransaction"]`).should('have.value', '86f6eda0-1e31-4511-81e8-b26311d4d632');

      cy.get(`[data-cy="libelle"]`).type('crac sous couleur de hors');
      cy.get(`[data-cy="libelle"]`).should('have.value', 'crac sous couleur de hors');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        typeTransaction = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', typeTransactionPageUrlPattern);
    });
  });
});
