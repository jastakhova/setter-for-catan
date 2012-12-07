'use strict';

function dump(object) {
    for (var propertyName in object) {
        console.log(propertyName + ' = ' + object[propertyName]);
    }
}

function getHexagonCoordinateId(x, y) {
    var r = 4 - (y + x);
    var c = 4 + Math.floor((y - x) / 2);

    return '(' + r + ',' + c + ')';
}

function SetterForCatanCtrl($scope) {
    $scope._ = _;

    $scope.rowCount = 12;
    $scope.columnCount = 12;
    // console.log(getHexagonCoordinateId(1, 0)); // (3,3)
    // console.log(getHexagonCoordinateId(0, 0)); // (4,4)
    // console.log(getHexagonCoordinateId(0, 1)); // (3,4)
    // console.log(getHexagonCoordinateId(1, 1)); // (2,4)
    $scope.boards = [
        'small',
        'small-spiral',
        'small-traders-and-barbarians',
        'small-traders-and-barbarians-spiral'];
    $scope.$watch('board', function(board) {
        if (board) {
            try {
                var tile = document.getElementById('(5,7)');
                tile.style.backgroundImage = "url('../images/forest.png')";
                //tile.style.backgroundImage = "none";
            } catch (e) {
                alert(e);
            }
        }
    });
    /*
    this.projects = [undefined, 'test-dashboard-test-input'];
    this.project = 'test-dashboard-test-input';
    this.classData = [
        {
            methodData: [
                {
                    name: 'test',
                    runtime: '',
                    status: 'NONE'
                }
            ],
            name: 'com.netflix.test_dashboard_test_input.TestDashboardUnitTestInput'
        },
        {
            methodData: [
                {
                    name: 'testFail',
                    runtime: '0.008',
                    stackTrace: [
                        'junit.framework.AssertionFailedError: ',
                        'at com.netflix.test_dashboard_test_input.TestDashboardSystemTestInput.testFail(TestDashboardSystemTestInput.java:28)'
                    ],
                    status: 'FAIL'
                },
                {
                    name: 'testIgnore',
                    runtime: '',
                    status: 'NONE'
                },
                {
                    name: 'testExpectedException',
                    runtime: '0.006',
                    stackTrace: [
                        'junit.framework.AssertionFailedError: Expected test to throw an instance of java.lang.Exception'
                    ],
                    status: 'FAIL'
                },
                {
                    name: 'testPass',
                    runtime: '0.005',
                    status: 'PASS'
                },
                {
                    name: 'testError',
                    runtime: '0.002',
                    stackTrace: [
                        'java.lang.Exception',
                        'at com.netflix.test_dashboard_test_input.TestDashboardSystemTestInput.testError(TestDashboardSystemTestInput.java:33)'
                    ],
                    status: 'ERROR'
                }
            ],
            name: 'com.netflix.test_dashboard_test_input.TestDashboardSystemTestInput'
        }
    ];
    */
}

SetterForCatanCtrl.$inject = ['$scope'];
