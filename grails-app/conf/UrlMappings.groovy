class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }
        "/" {
            controller = "Report"
            action = "getReports"
        }

        "/post/report"(controller: "Report", action: "add", parseRequest: true)
        "/put/report"(controller: "Report", action: "update", parseRequest: true)
        "/get/reports/$max?/$offset?"(controller: "Report", action: "getReports")
        "/get/report/$id?"(controller: "Report", action: "getReport")
        "/get/reports/params/$login?/$projectName?"(controller: "Report", action: "getReportByParams")
        "/get/projects/$max?/$offset?"(controller: "Report", action: "getProjects")
        "/"(view: "/report/index") { [controller = "Report", action = "getReports", max = 100, offset = 0] }

        "/json/reports/$max?/$offset?"(controller: "ReportRest", action: "getReports")
        "/json/save"(controller: "ReportRest", action: "save", parseRequest: true)
        "/json/delete/$id?"(controller: "ReportRest", action: "delete")
//        "/"(view: "/index")
        "500"(view: '/error')
    }
}
