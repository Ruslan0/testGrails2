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
        "/get/reports/$max?/$offset?/$format?"(controller: "Report", action: "getReports")
        "/get/report/$id?/$format?"(controller: "Report", action: "getReport")
        "/get/reports/params/$login?/$projectName?"(controller: "Report", action: "getReportByParams")
        "/get/projects/$max?/$offset?"(controller: "Report", action: "getProjects")
        "/"(view: "/report/index") { [controller = "Report", action = "getReports", max = 100, offset = 0] }

        "500"(view: '/error')
    }
}
