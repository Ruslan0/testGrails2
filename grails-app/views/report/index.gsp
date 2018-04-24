<%@ page import="reports.Report" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'report.label', default: 'Report')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-report" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                             default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
  <ul>
    <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
  </ul>
</div>

<div id="list-report" class="content scaffold-list" role="main">
  <h1><g:message code="default.list.label" args="[entityName]"/></h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <table>
    <thead>
    <tr>

      <g:sortableColumn property="id" title="${message(code: 'report.id.label', default: 'ID')}"/>

      <g:sortableColumn property="project" title="${message(code: 'report.project.label', default: 'Project')}"/>

      <g:sortableColumn property="currdate" title="${message(code: 'report.currdate.label', default: 'Currdate')}"/>

      <g:sortableColumn property="hours" title="${message(code: 'report.hours.label', default: 'Hours')}"/>

      <g:sortableColumn property="note" title="${message(code: 'report.note.label', default: 'Note')}"/>

      <g:sortableColumn property="login" title="${message(code: 'report.login.label', default: 'Login')}"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${reports}" status="i" var="reportInstance">
      <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

        <td><g:link action="getReport"
                    id="${reportInstance.id}">${fieldValue(bean: reportInstance, field: "id")}</g:link></td>

        <td>${fieldValue(bean: reportInstance, field: "assigned.project.name")}</td>

        <td>${formatDate(format:'dd.MM.yyyy',date:reportInstance?.currdate)}</td>

        <td>${fieldValue(bean: reportInstance, field: "hours")}</td>

        <td>${fieldValue(bean: reportInstance, field: "note")}</td>

        <td>${fieldValue(bean: reportInstance, field: "assigned.employer.login")}</td>

      </tr>
    </g:each>
    </tbody>
  </table>

  <div class="pagination">
    <g:paginate total="${reports.size() ?: 0}"/>
  </div>
</div>
</body>
</html>
