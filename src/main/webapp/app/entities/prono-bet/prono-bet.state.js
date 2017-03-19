(function() {
    'use strict';

    angular
        .module('adaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('prono-bet', {
            parent: 'entity',
            url: '/prono-bet',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'adaApp.pronoBet.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prono-bet/prono-bets.html',
                    controller: 'PronoBetController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pronoBet');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('prono-bet-detail', {
            parent: 'prono-bet',
            url: '/prono-bet/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'adaApp.pronoBet.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prono-bet/prono-bet-detail.html',
                    controller: 'PronoBetDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pronoBet');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PronoBet', function($stateParams, PronoBet) {
                    return PronoBet.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'prono-bet',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('prono-bet-detail.edit', {
            parent: 'prono-bet-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prono-bet/prono-bet-dialog.html',
                    controller: 'PronoBetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PronoBet', function(PronoBet) {
                            return PronoBet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prono-bet.new', {
            parent: 'prono-bet',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prono-bet/prono-bet-dialog.html',
                    controller: 'PronoBetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                user: null,
                                score1: null,
                                score2: null,
                                result: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('prono-bet', null, { reload: 'prono-bet' });
                }, function() {
                    $state.go('prono-bet');
                });
            }]
        })
        .state('prono-bet.edit', {
            parent: 'prono-bet',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prono-bet/prono-bet-dialog.html',
                    controller: 'PronoBetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PronoBet', function(PronoBet) {
                            return PronoBet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prono-bet', null, { reload: 'prono-bet' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prono-bet.delete', {
            parent: 'prono-bet',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prono-bet/prono-bet-delete-dialog.html',
                    controller: 'PronoBetDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PronoBet', function(PronoBet) {
                            return PronoBet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prono-bet', null, { reload: 'prono-bet' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
