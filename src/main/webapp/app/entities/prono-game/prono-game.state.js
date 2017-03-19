(function() {
    'use strict';

    angular
        .module('adaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('prono-game', {
            parent: 'entity',
            url: '/prono-game',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'adaApp.pronoGame.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prono-game/prono-games.html',
                    controller: 'PronoGameController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pronoGame');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('prono-game-detail', {
            parent: 'prono-game',
            url: '/prono-game/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'adaApp.pronoGame.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prono-game/prono-game-detail.html',
                    controller: 'PronoGameDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pronoGame');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PronoGame', function($stateParams, PronoGame) {
                    return PronoGame.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'prono-game',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('prono-game-detail.edit', {
            parent: 'prono-game-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prono-game/prono-game-dialog.html',
                    controller: 'PronoGameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PronoGame', function(PronoGame) {
                            return PronoGame.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prono-game.new', {
            parent: 'prono-game',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prono-game/prono-game-dialog.html',
                    controller: 'PronoGameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                gameNumber: null,
                                date: null,
                                team1: null,
                                team2: null,
                                score1: null,
                                score2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('prono-game', null, { reload: 'prono-game' });
                }, function() {
                    $state.go('prono-game');
                });
            }]
        })
        .state('prono-game.edit', {
            parent: 'prono-game',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prono-game/prono-game-dialog.html',
                    controller: 'PronoGameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PronoGame', function(PronoGame) {
                            return PronoGame.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prono-game', null, { reload: 'prono-game' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prono-game.delete', {
            parent: 'prono-game',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prono-game/prono-game-delete-dialog.html',
                    controller: 'PronoGameDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PronoGame', function(PronoGame) {
                            return PronoGame.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prono-game', null, { reload: 'prono-game' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
