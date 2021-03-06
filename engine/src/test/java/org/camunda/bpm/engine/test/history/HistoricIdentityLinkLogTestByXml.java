package org.camunda.bpm.engine.test.history;

import java.util.List;

import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.history.HistoricIdentityLinkLog;
import org.camunda.bpm.engine.history.HistoricIdentityLinkLogQuery;
import org.camunda.bpm.engine.impl.test.PluggableProcessEngineTestCase;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.RequiredHistoryLevel;

@RequiredHistoryLevel(ProcessEngineConfiguration.HISTORY_FULL)
public class HistoricIdentityLinkLogTestByXml extends PluggableProcessEngineTestCase{

  private static String PROCESS_DEFINITION_KEY_CANDIDATE_USER = "oneTaskProcessForHistoricIdentityLinkWithCanidateUser";
  private static String PROCESS_DEFINITION_KEY_CANDIDATE_GROUP = "oneTaskProcessForHistoricIdentityLinkWithCanidateGroup";
  private static String PROCESS_DEFINITION_KEY_ASSIGNEE = "oneTaskProcessForHistoricIdentityLinkWithAssignee";
  private static String PROCESS_DEFINITION_KEY_CANDIDATE_STARTER_USER = "oneTaskProcessForHistoricIdentityLinkWithCanidateStarterUsers";
  private static String PROCESS_DEFINITION_KEY_CANDIDATE_STARTER_GROUP = "oneTaskProcessForHistoricIdentityLinkWithCanidateStarterGroups";
  private static final String XML_USER = "demo";
  private static final String XML_GROUP = "demoGroups";
  private static final String XML_ASSIGNEE = "assignee";

  protected static final String TENANT_ONE = "tenant1";

  protected static final String CANDIDATE_STARTER_USER = "org/camunda/bpm/engine/test/api/repository/ProcessDefinitionCandidateTest.testCandidateStarterUser.bpmn20.xml";
  protected static final String CANDIDATE_STARTER_USERS = "org/camunda/bpm/engine/test/api/repository/ProcessDefinitionCandidateTest.testCandidateStarterUsers.bpmn20.xml";

  protected static final String CANDIDATE_STARTER_GROUP = "org/camunda/bpm/engine/test/api/repository/ProcessDefinitionCandidateTest.testCandidateStarterGroup.bpmn20.xml";
  protected static final String CANDIDATE_STARTER_GROUPS = "org/camunda/bpm/engine/test/api/repository/ProcessDefinitionCandidateTest.testCandidateStarterGroups.bpmn20.xml";

  @Deployment(resources = { "org/camunda/bpm/engine/test/api/runtime/OneTaskProcessWithCandidateUser.bpmn20.xml" })
  public void testShouldAddTaskCandidateforAddIdentityLinkUsingXml() {

    // Pre test
    List<HistoricIdentityLinkLog> historicIdentityLinks = historyService.createHistoricIdentityLinkLogQuery().list();
    assertEquals(historicIdentityLinks.size(), 0);

    // given
    startProcessInstance(PROCESS_DEFINITION_KEY_CANDIDATE_USER);
    historicIdentityLinks = historyService.createHistoricIdentityLinkLogQuery().list();
    assertEquals(historicIdentityLinks.size(), 1);

    // query Test
    HistoricIdentityLinkLogQuery query = historyService.createHistoricIdentityLinkLogQuery();
    assertEquals(query.userId(XML_USER).count(), 1);
  }

  @Deployment(resources = { "org/camunda/bpm/engine/test/api/runtime/OneTaskProcessWithTaskAssignee.bpmn20.xml" })
  public void testShouldAddTaskAssigneeforAddIdentityLinkUsingXml() {

    // Pre test
    List<HistoricIdentityLinkLog> historicIdentityLinks = historyService.createHistoricIdentityLinkLogQuery().list();
    assertEquals(historicIdentityLinks.size(), 0);

    // given
    startProcessInstance(PROCESS_DEFINITION_KEY_ASSIGNEE);
    historicIdentityLinks = historyService.createHistoricIdentityLinkLogQuery().list();
    assertEquals(historicIdentityLinks.size(), 1);

    // query Test
    HistoricIdentityLinkLogQuery query = historyService.createHistoricIdentityLinkLogQuery();
    assertEquals(query.userId(XML_ASSIGNEE).count(), 1);


  }

  @Deployment(resources = { "org/camunda/bpm/engine/test/api/runtime/OneTaskProcessWithCandidateGroups.bpmn20.xml" })
  public void testShouldAddTaskCandidateGroupforAddIdentityLinkUsingXml() {

    // Pre test
    List<HistoricIdentityLinkLog> historicIdentityLinks = historyService.createHistoricIdentityLinkLogQuery().list();
    assertEquals(historicIdentityLinks.size(), 0);

    // given
    startProcessInstance(PROCESS_DEFINITION_KEY_CANDIDATE_GROUP);
    historicIdentityLinks = historyService.createHistoricIdentityLinkLogQuery().list();
    assertEquals(historicIdentityLinks.size(), 1);

    // query Test
    HistoricIdentityLinkLogQuery query = historyService.createHistoricIdentityLinkLogQuery();
    assertEquals(query.groupId(XML_GROUP).count(), 1);
  }

  @Deployment(resources = { "org/camunda/bpm/engine/test/api/runtime/OneTaskProcessWithCandidateStarterUsers.bpmn20.xml" })
  public void testShouldAddProcessCandidateStarterUserforAddIdentityLinkUsingXml() {

    // Pre test - Historical identity link is added as part of deployment
    List<HistoricIdentityLinkLog> historicIdentityLinks = historyService.createHistoricIdentityLinkLogQuery().list();
    assertEquals(historicIdentityLinks.size(), 1);

    // given
    ProcessDefinition latestProcessDef = repositoryService.createProcessDefinitionQuery().processDefinitionKey(PROCESS_DEFINITION_KEY_CANDIDATE_STARTER_USER)
        .singleResult();
    assertNotNull(latestProcessDef);

    List<IdentityLink> links = repositoryService.getIdentityLinksForProcessDefinition(latestProcessDef.getId());
    assertEquals(1, links.size());

    historicIdentityLinks = historyService.createHistoricIdentityLinkLogQuery().list();
    assertEquals(historicIdentityLinks.size(), 1);

    // query Test
    HistoricIdentityLinkLogQuery query = historyService.createHistoricIdentityLinkLogQuery();
    assertEquals(query.userId(XML_USER).count(), 1);
  }
  @Deployment(resources = { "org/camunda/bpm/engine/test/api/runtime/OneTaskProcessWithCandidateStarterGroups.bpmn20.xml" })
  public void testShouldAddProcessCandidateStarterGroupforAddIdentityLinkUsingXml() {

    // Pre test - Historical identity link is added as part of deployment
    List<HistoricIdentityLinkLog> historicIdentityLinks = historyService.createHistoricIdentityLinkLogQuery().list();
    assertEquals(historicIdentityLinks.size(), 1);

    // given
    ProcessDefinition latestProcessDef = repositoryService.createProcessDefinitionQuery().processDefinitionKey(PROCESS_DEFINITION_KEY_CANDIDATE_STARTER_GROUP)
        .singleResult();
    assertNotNull(latestProcessDef);

    List<IdentityLink> links = repositoryService.getIdentityLinksForProcessDefinition(latestProcessDef.getId());
    assertEquals(1, links.size());

    historicIdentityLinks = historyService.createHistoricIdentityLinkLogQuery().list();
    assertEquals(historicIdentityLinks.size(), 1);

    // query Test
    HistoricIdentityLinkLogQuery query = historyService.createHistoricIdentityLinkLogQuery();
    assertEquals(query.groupId(XML_GROUP).count(), 1);
  }

  public void testPropagateTenantIdToCandidateStarterUser() {
    // when
    org.camunda.bpm.engine.repository.Deployment deployment = repositoryService.createDeployment()
      .addClasspathResource(CANDIDATE_STARTER_USER)
      .tenantId(TENANT_ONE)
      .deploy();

    // then
    List<HistoricIdentityLinkLog> historicLinks = historyService.createHistoricIdentityLinkLogQuery().list();
    assertEquals(historicLinks.size(), 1);

    HistoricIdentityLinkLog historicLink = historicLinks.get(0);
    assertNotNull(historicLink.getTenantId());
    assertEquals(TENANT_ONE, historicLink.getTenantId());

    repositoryService.deleteDeployment(deployment.getId(), true);
  }

  public void testPropagateTenantIdToCandidateStarterUsers() {
    // when
    org.camunda.bpm.engine.repository.Deployment deployment = repositoryService.createDeployment()
        .addClasspathResource(CANDIDATE_STARTER_USERS)
        .tenantId(TENANT_ONE)
        .deploy();

      // then
      List<HistoricIdentityLinkLog> historicLinks = historyService.createHistoricIdentityLinkLogQuery().list();
      assertEquals(3, historicLinks.size());

    for (HistoricIdentityLinkLog historicLink : historicLinks) {
      assertNotNull(historicLink.getTenantId());
      assertEquals(TENANT_ONE, historicLink.getTenantId());
    }

    repositoryService.deleteDeployment(deployment.getId(), true);
  }

  public void testPropagateTenantIdToCandidateStarterGroup() {
    // when
    org.camunda.bpm.engine.repository.Deployment deployment = repositoryService.createDeployment()
        .addClasspathResource(CANDIDATE_STARTER_GROUP)
        .tenantId(TENANT_ONE)
        .deploy();

      // then
      List<HistoricIdentityLinkLog> historicLinks = historyService.createHistoricIdentityLinkLogQuery().list();
      assertEquals(historicLinks.size(), 1);

      HistoricIdentityLinkLog historicLink = historicLinks.get(0);
      assertNotNull(historicLink.getTenantId());
      assertEquals(TENANT_ONE, historicLink.getTenantId());

      repositoryService.deleteDeployment(deployment.getId(), true);
  }

  public void testPropagateTenantIdToCandidateStarterGroups() {
    // when
    org.camunda.bpm.engine.repository.Deployment deployment = repositoryService.createDeployment()
        .addClasspathResource(CANDIDATE_STARTER_GROUPS)
        .tenantId(TENANT_ONE)
        .deploy();

      // then
      List<HistoricIdentityLinkLog> historicLinks = historyService.createHistoricIdentityLinkLogQuery().list();
      assertEquals(3, historicLinks.size());

    for (HistoricIdentityLinkLog historicLink : historicLinks) {
      assertNotNull(historicLink.getTenantId());
      assertEquals(TENANT_ONE, historicLink.getTenantId());
    }

    repositoryService.deleteDeployment(deployment.getId(), true);
  }

  protected ProcessInstance startProcessInstance(String key) {
    return runtimeService.startProcessInstanceByKey(key);
  }
}
