
<%@ page import="reports.Report" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'report.label', default: 'Report')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-report" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-report" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:if test="${flash.error}">
				<div class="errors">${flash.error}</div>
			</g:if>

			<ol class="property-list report">

				<g:if test="${reportInstance?.id}">
					<li class="fieldcontain">
						<span id="id-label" class="property-label"><g:message code="report.id.label" default="Id" /></span>

						<span class="property-value" aria-labelledby="id-label"><g:fieldValue bean="${reportInstance}" field="id"/></span>

					</li>
				</g:if>

				<g:if test="${reportInstance?.assigned.project.name}">
					<li class="fieldcontain">
						<span id="project-label" class="property-label"><g:message code="report.project.label" default="Project" /></span>

						<span class="property-value" aria-labelledby="project-label"><g:fieldValue bean="${reportInstance}" field="assigned.project.name"/></span>

					</li>
				</g:if>

				<g:if test="${reportInstance?.currdate}">
				<li class="fieldcontain">
					<span id="currdate-label" class="property-label"><g:message code="report.currdate.label" default="Currdate" /></span>
					
					<span class="property-value" aria-labelledby="currdate-label"><g:formatDate  format="dd.MM.yyyy" date="${reportInstance?.currdate}" /></span>

				</li>
				</g:if>
			
				<g:if test="${reportInstance?.hours}">
				<li class="fieldcontain">
					<span id="hours-label" class="property-label"><g:message code="report.hours.label" default="Hours" /></span>
					
						<span class="property-value" aria-labelledby="hours-label"><g:fieldValue bean="${reportInstance}" field="hours"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportInstance?.note}">
				<li class="fieldcontain">
					<span id="note-label" class="property-label"><g:message code="report.note.label" default="Note" /></span>
					
						<span class="property-value" aria-labelledby="note-label"><g:fieldValue bean="${reportInstance}" field="note"/></span>
					
				</li>
				</g:if>

				<g:if test="${reportInstance?.assigned.employer.login}">
					<li class="fieldcontain">
						<span id="employer-label" class="property-label"><g:message code="employer.employer.label" default="Employer" /></span>

						<span class="property-value" aria-labelledby="employer-label"><g:fieldValue bean="${reportInstance}" field="assigned.employer.login"/></span>

					</li>
				</g:if>

			</ol>
			<g:form url="[resource:reportInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${reportInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
