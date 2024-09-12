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

describe('DispositifSercurite e2e test', () => {
  const dispositifSercuritePageUrl = '/dispositif-sercurite';
  const dispositifSercuritePageUrlPattern = new RegExp('/dispositif-sercurite(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const dispositifSercuriteSample = {};

  let dispositifSercurite;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/dispositif-sercurites+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/dispositif-sercurites').as('postEntityRequest');
    cy.intercept('DELETE', '/api/dispositif-sercurites/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (dispositifSercurite) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/dispositif-sercurites/${dispositifSercurite.id}`,
      }).then(() => {
        dispositifSercurite = undefined;
      });
    }
  });

  it('DispositifSercurites menu should load DispositifSercurites page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('dispositif-sercurite');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DispositifSercurite').should('exist');
    cy.url().should('match', dispositifSercuritePageUrlPattern);
  });

  describe('DispositifSercurite page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(dispositifSercuritePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DispositifSercurite page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/dispositif-sercurite/new$'));
        cy.getEntityCreateUpdateHeading('DispositifSercurite');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dispositifSercuritePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/dispositif-sercurites',
          body: dispositifSercuriteSample,
        }).then(({ body }) => {
          dispositifSercurite = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/dispositif-sercurites+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/dispositif-sercurites?page=0&size=20>; rel="last",<http://localhost/api/dispositif-sercurites?page=0&size=20>; rel="first"',
              },
              body: [dispositifSercurite],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(dispositifSercuritePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details DispositifSercurite page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('dispositifSercurite');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dispositifSercuritePageUrlPattern);
      });

      it('edit button click should load edit DispositifSercurite page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DispositifSercurite');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dispositifSercuritePageUrlPattern);
      });

      it('edit button click should load edit DispositifSercurite page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DispositifSercurite');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dispositifSercuritePageUrlPattern);
      });

      it('last delete button click should delete instance of DispositifSercurite', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('dispositifSercurite').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dispositifSercuritePageUrlPattern);

        dispositifSercurite = undefined;
      });
    });
  });

  describe('new DispositifSercurite page', () => {
    beforeEach(() => {
      cy.visit(`${dispositifSercuritePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DispositifSercurite');
    });

    it('should create an instance of DispositifSercurite', () => {
      cy.get(`[data-cy="idCanal"]`).type('amorphe totalement');
      cy.get(`[data-cy="idCanal"]`).should('have.value', 'amorphe totalement');

      cy.get(`[data-cy="idTypeTransaction"]`).type('athlète engloutir pacifique');
      cy.get(`[data-cy="idTypeTransaction"]`).should('have.value', 'athlète engloutir pacifique');

      cy.get(`[data-cy="idDispositif"]`).type('claquer');
      cy.get(`[data-cy="idDispositif"]`).should('have.value', 'claquer');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        dispositifSercurite = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', dispositifSercuritePageUrlPattern);
    });
  });
});
