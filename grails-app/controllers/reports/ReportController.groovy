package reports

class ReportController {

    static allowedMethods = [add: "POST", update: "PUT", getReports: "GET", getReport: "GET", getProjects: "GET", getReportByLogin: "GET", getReportByParams: "GET"]

    def reportService
    def projectService

    def maxDefault = 100
    def offsetDefault = 0

    def getReports(Integer max, Integer offset) {
        def reportList = reportService.getReports(max ?: maxDefault, offset ?: offsetDefault)
        render(view: "/report/index", model: [reports: reportList])
    }

    def getProjects(Integer max, Integer offset) {
        respond projectService.getProjects(max ?: maxDefault, offset ?: offsetDefault)
    }

    def getReport(Integer id) {
        def reportInstance = reportService.getReport(id)
        render(view: "/report/show", model: [reportInstance: reportInstance])
    }

    def getReportByParams(String login, String projectName) {
        if (projectName && login)
            respond reportService.getReportByParams(login, projectName)
        else if (login && !projectName)
            respond reportService.getReportByLogin(login)
        else if (!projectName && !login)
            respond reportService.getReports(maxDefault, offsetDefault)
    }

    def save(Report reportInstance) {
        def resStatus = reportService.save(reportInstance)
        switch (resStatus) {
            case 400:
                flash.error = reportService.getMessageFailedData(reportInstance)
                render(view: "/report/show", model: [reportInstance: reportInstance])
                break
            case 404:
                flash.error =  message(code: 'report.invalid,save.form.notfound', args: [reportInstance.id])
                render(view: "/report/show", model: [reportInstance: reportInstance])
                break
            case 409:
                flash.error = message(code: 'report.invalid,save.conflict', default: 'Resources conflict')
                render(view: "/report/show", model: [reportInstance: reportInstance])
                break
            case 412:
                flash.error = message(code: 'report.invalid,save.precondition', default: 'Precondition Failed')
                render(view: "/report/show", model: [reportInstance: reportInstance])
            case 201:
                flash.message = message(code: 'default.created.message', args: [message(code: 'reportInstance.label', default: 'Report'), reportInstance.id])
                redirect(action: "getReport", id: reportInstance.id)
                break
        }
    }

    def delete(Long id) {
        def resStatus = reportService.delete(id)
        switch (resStatus) {
            case 404:
                flash.error =  message(code: 'report.invalid,save.form.notfound', args: [id])
                break
            case 409:
                flash.error = message(code: 'report.invalid,detete', args: [id])
                break
            case 201:
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'reportInstance.label', default: 'Report'), id])
                break
        }
        redirect(action: "getReports")

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
