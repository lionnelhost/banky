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

describe('DispositifSignature e2e test', () => {
  const dispositifSignaturePageUrl = '/dispositif-signature';
  const dispositifSignaturePageUrlPattern = new RegExp('/dispositif-signature(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const dispositifSignatureSample = {};

  let dispositifSignature;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/dispositif-signatures+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/dispositif-signatures').as('postEntityRequest');
    cy.intercept('DELETE', '/api/dispositif-signatures/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (dispositifSignature) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/dispositif-signatures/${dispositifSignature.idDispositif}`,
      }).then(() => {
        dispositifSignature = undefined;
      });
    }
  });

  it('DispositifSignatures menu should load DispositifSignatures page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('dispositif-signature');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DispositifSignature').should('exist');
    cy.url().should('match', dispositifSignaturePageUrlPattern);
  });

  describe('DispositifSignature page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(dispositifSignaturePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DispositifSignature page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/dispositif-signature/new$'));
        cy.getEntityCreateUpdateHeading('DispositifSignature');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dispositifSignaturePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/dispositif-signatures',
          body: dispositifSignatureSample,
        }).then(({ body }) => {
          dispositifSignature = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/dispositif-signatures+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/dispositif-signatures?page=0&size=20>; rel="last",<http://localhost/api/dispositif-signatures?page=0&size=20>; rel="first"',
              },
              body: [dispositifSignature],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(dispositifSignaturePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details DispositifSignature page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('dispositifSignature');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dispositifSignaturePageUrlPattern);
      });

      it('edit button click should load edit DispositifSignature page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DispositifSignature');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dispositifSignaturePageUrlPattern);
      });

      it('edit button click should load edit DispositifSignature page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DispositifSignature');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dispositifSignaturePageUrlPattern);
      });

      it('last delete button click should delete instance of DispositifSignature', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('dispositifSignature').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dispositifSignaturePageUrlPattern);

        dispositifSignature = undefined;
      });
    });
  });

  describe('new DispositifSignature page', () => {
    beforeEach(() => {
      cy.visit(`${dispositifSignaturePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DispositifSignature');
    });

    it('should create an instance of DispositifSignature', () => {
      cy.get(`[data-cy="idDispositif"]`).type('07d95d27-4f29-49d2-9efa-cbee20b140ea');
      cy.get(`[data-cy="idDispositif"]`).should('have.value', '07d95d27-4f29-49d2-9efa-cbee20b140ea');

      cy.get(`[data-cy="libelle"]`).type('ici au-dessous de pourpre');
      cy.get(`[data-cy="libelle"]`).should('have.value', 'ici au-dessous de pourpre');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        dispositifSignature = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', dispositifSignaturePageUrlPattern);
    });
  });
});
