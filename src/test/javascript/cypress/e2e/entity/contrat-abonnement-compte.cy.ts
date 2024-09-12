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

describe('ContratAbonnementCompte e2e test', () => {
  const contratAbonnementComptePageUrl = '/contrat-abonnement-compte';
  const contratAbonnementComptePageUrlPattern = new RegExp('/contrat-abonnement-compte(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const contratAbonnementCompteSample = {};

  let contratAbonnementCompte;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/contrat-abonnement-comptes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/contrat-abonnement-comptes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/contrat-abonnement-comptes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (contratAbonnementCompte) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/contrat-abonnement-comptes/${contratAbonnementCompte.id}`,
      }).then(() => {
        contratAbonnementCompte = undefined;
      });
    }
  });

  it('ContratAbonnementComptes menu should load ContratAbonnementComptes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('contrat-abonnement-compte');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ContratAbonnementCompte').should('exist');
    cy.url().should('match', contratAbonnementComptePageUrlPattern);
  });

  describe('ContratAbonnementCompte page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(contratAbonnementComptePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ContratAbonnementCompte page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/contrat-abonnement-compte/new$'));
        cy.getEntityCreateUpdateHeading('ContratAbonnementCompte');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contratAbonnementComptePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/contrat-abonnement-comptes',
          body: contratAbonnementCompteSample,
        }).then(({ body }) => {
          contratAbonnementCompte = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/contrat-abonnement-comptes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/contrat-abonnement-comptes?page=0&size=20>; rel="last",<http://localhost/api/contrat-abonnement-comptes?page=0&size=20>; rel="first"',
              },
              body: [contratAbonnementCompte],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(contratAbonnementComptePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ContratAbonnementCompte page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('contratAbonnementCompte');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contratAbonnementComptePageUrlPattern);
      });

      it('edit button click should load edit ContratAbonnementCompte page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ContratAbonnementCompte');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contratAbonnementComptePageUrlPattern);
      });

      it('edit button click should load edit ContratAbonnementCompte page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ContratAbonnementCompte');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contratAbonnementComptePageUrlPattern);
      });

      it('last delete button click should delete instance of ContratAbonnementCompte', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('contratAbonnementCompte').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contratAbonnementComptePageUrlPattern);

        contratAbonnementCompte = undefined;
      });
    });
  });

  describe('new ContratAbonnementCompte page', () => {
    beforeEach(() => {
      cy.visit(`${contratAbonnementComptePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ContratAbonnementCompte');
    });

    it('should create an instance of ContratAbonnementCompte', () => {
      cy.get(`[data-cy="idContrat"]`).type('tellement');
      cy.get(`[data-cy="idContrat"]`).should('have.value', 'tellement');

      cy.get(`[data-cy="idAbonne"]`).type('apprécier rire sitôt que');
      cy.get(`[data-cy="idAbonne"]`).should('have.value', 'apprécier rire sitôt que');

      cy.get(`[data-cy="idCompteBancaire"]`).type('raide');
      cy.get(`[data-cy="idCompteBancaire"]`).should('have.value', 'raide');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        contratAbonnementCompte = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', contratAbonnementComptePageUrlPattern);
    });
  });
});
