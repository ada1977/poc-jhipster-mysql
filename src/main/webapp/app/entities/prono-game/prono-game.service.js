(function() {
    'use strict';
    angular
        .module('adaApp')
        .factory('PronoGame', PronoGame);

    PronoGame.$inject = ['$resource', 'DateUtils'];

    function PronoGame ($resource, DateUtils) {
        var resourceUrl =  'api/prono-games/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
