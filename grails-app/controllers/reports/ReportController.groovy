package reports

import grails.converters.JSON

class ReportController {

    static allowedMethods = [add: "POST", getReports: "GET", getReport: "GET", getProjects: "GET", getReportByLogin: "GET", getReportByParams: "GET"]
    static responseFormats = ['json']

    def reportService
    def maxDefault = 100
    def offsetDefault = 0

    def getReports(Integer max, Integer offset, String format) {
        def reportLiat = reportService.getReports(max ?: maxDefault, offset ?: offsetDefault)
        if ((format) && (format == "json"))
            respond reportLiat, [formats: ['json']]
        else
            render(view: "/report/index", model: [reports: reportLiat])
    }

    def getProjects(Integer max, Integer offset) {
        respond reportService.getProjects(max ?: maxDefault, offset ?: offsetDefault)
    }

    def getReport(Integer id, String format) {
        def reportInstance = reportService.getReport(id)
        if ((format) && (format == "json"))
            respond reportInstance
        else
            render(view: "/report/show", model: [reportInstance: reportInstance])
    }

    def getReportByParams(String login, String projectName) {
        if (projectName && login)
            respond reportService.getReportByParams(login, projectName)
        else if (login)
            respond reportService.getReportByLogin(login)
        else if (!projectName && login)
            respond reportService.getReports(maxDefault, offsetDefault)
    }


    def add(Report reportInstance) {
        def resStatus = reportService.add(reportInstance)
        switch (resStatus) {
            case 400:
                flash.message = 'The request was well-formed but was unable to be followed due to semantic errors'
                render(status: resStatus, text: reportService.getMessageFailedData(reportInstance))
                break
            case 409:
                flash.message = 'The request could not be completed due to a conflict with the current state of the target resource.'
                render(status: resStatus, text: reportService.getMessageFailedData(reportInstance))
                break
            case 201:
                flash.message = message(code: 'default.created.message', args: [message(code: 'reportInstance.label', default: 'Report'), reportInstance.id])
                render(status: resStatus, text: flash.message)
                break
        }
    }

}
