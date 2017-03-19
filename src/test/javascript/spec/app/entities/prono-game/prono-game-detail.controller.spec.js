'use strict';

describe('Controller Tests', function() {

    describe('PronoGame Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPronoGame, MockPronoLeague, MockPronoBet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPronoGame = jasmine.createSpy('MockPronoGame');
            MockPronoLeague = jasmine.createSpy('MockPronoLeague');
            MockPronoBet = jasmine.createSpy('MockPronoBet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PronoGame': MockPronoGame,
                'PronoLeague': MockPronoLeague,
                'PronoBet': MockPronoBet
            };
            createController = function() {
                $injector.get('$controller')("PronoGameDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'adaApp:pronoGameUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
