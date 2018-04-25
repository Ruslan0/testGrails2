package reports

class ReportController {

    static allowedMethods = [add: "POST", update: "PUT", getReports: "GET", getReport: "GET", getProjects: "GET", getReportByLogin: "GET", getReportByParams: "GET"]

    def reportService
    def maxDefault = 100
    def offsetDefault = 0

    def getReports(Integer max, Integer offset, String format) {
        def reportLiat = reportService.getReports(max ?: maxDefault, offset ?: offsetDefault)
        if (format)
            respond reportLiat, [formats: [format]]
        else {
            flash.clear()
            render(view: "/report/index", model: [reports: reportLiat])
        }
    }

    def getProjects(Integer max, Integer offset) {
        respond reportService.getProjects(max ?: maxDefault, offset ?: offsetDefault)
    }

    def getReport(Integer id, String format) {
        def reportInstance = reportService.getReport(id)
        if (format)
            respond reportInstance, [formats: [format]]
        else {
            flash.clear()
            render(view: "/report/show", model: [reportInstance: reportInstance])
        }
    }

    def getReportByParams(String login, String projectName) {
        if (projectName && login)
            respond reportService.getReportByParams(login, projectName)
        else if (login)
            respond reportService.getReportByLogin(login)
        else if (!projectName && login)
            respond reportService.getReports(maxDefault, offsetDefault)
    }

    def save(Report reportInstance) {
        def resStatus = reportService.save(reportInstance)
        switch (resStatus) {
            case 400:
                flash.message = 'The request was well-formed but was unable to be followed due to semantic errors'
                render ([reportInstance: reportInstance, success: false, error: message(error: reportService.getMessageFailedData(reportInstance))])
                break
            case 409:
                flash.message = 'The request could not be completed due to a conflict with the current state of the target resource.'
                render ([success: false, error: message(error: reportService.getMessageFailedData(reportInstance))])
                break
            case 201:
                flash.message = message(code: 'default.created.message', args: [message(code: 'reportInstance.label', default: 'Report'), reportInstance.id])
                render(view: "/report/show", model: [reportInstance: reportInstance])
                break
        }
    }

    def delete(Long id) {
        def resStatus = reportService.delete(id)
        switch (resStatus) {
            case 404:
                flash.message = 'The request was well-formed but was unable to be followed due to semantic errors'
                render ([success: false, error: message(error: "Resource not found (id = "+id+")")])
                break
            case 409:
                flash.message = 'The request could not be completed due to a conflict with the current state of the target resource.'
                render ([success: false, error: message(error: "the record with id "+id+"could not be deleted ")])
                break
            case 201:
                getReports()
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'reportInstance.label', default: 'Report'), id])
                break
        }
    }

    def add(Report reportInstance) {
        save(reportInstance)
    }

    def update(Report reportInstance) {
        save(reportInstance)
    }

    def create() {
        Report reportInstance = new Report(params)
        // set default values
        reportInstance.currdate = new Date()
        reportInstance.hours = 8
        respond reportInstance
    }


    def edit(Report reportInstance) {
        respond reportInstance
    }

}
