<%@ page import="reports.Report" %>



<div class="fieldcontain ${hasErrors(bean: reportInstance, field: 'currdate', 'error')} ">
	<label for="currdate">
		<g:message code="report.currdate.label" default="Currdate" />
		
	</label>
	<g:datePicker name="currdate" precision="day"  value="${reportInstance?.currdate}" default="none" noSelection="['': '']" />

</div>

<div class="fieldcontain ${hasErrors(bean: reportInstance, field: 'hours', 'error')} required">
	<label for="hours">
		<g:message code="report.hours.label" default="Hours" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="hours" type="number" max="24" value="${reportInstance.hours}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: reportInstance, field: 'note', 'error')} required">
	<label for="note">
		<g:message code="report.note.label" default="Note" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="note" required="" value="${reportInstance?.note}"/>

</div>

