/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.engine;

import java.util.Collection;
import java.util.Map;

import org.camunda.bpm.engine.form.StartFormData;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;


/** Access to form data and rendered forms for starting new process instances and completing tasks.
 *
 * @author Tom Baeyens
 * @author Falko Menge (camunda)
 */
public interface FormService {

  /** Retrieves all data necessary for rendering a form to start a new process instance. This can be used to perform rendering of the forms outside of the process engine. */
  StartFormData getStartFormData(String processDefinitionId);

  /** Rendered form generated by the default build-in form engine for starting a new process instance. */
  Object getRenderedStartForm(String processDefinitionId);

  /** Rendered form generated by the given build-in form engine for starting a new process instance. */
  Object getRenderedStartForm(String processDefinitionId, String formEngineName);

  /**
   * @deprecated use {@link #submitStartForm(String, Map)}
   * */
  @Deprecated
  ProcessInstance submitStartFormData(String processDefinitionId, Map<String, String> properties);

  /** Start a new process instance with the user data that was entered as properties in a start form. */
  ProcessInstance submitStartForm(String processDefinitionId, Map<String, Object> properties);

  /**
   * @deprecated use {@link #submitStartForm(String, String, Map)}
   */
  @Deprecated
  ProcessInstance submitStartFormData(String processDefinitionId, String businessKey, Map<String, String> properties);

  /**
   * Start a new process instance with the user data that was entered as properties in a start form.
   *
   * A business key can be provided to associate the process instance with a
   * certain identifier that has a clear business meaning. For example in an
   * order process, the business key could be an order id. This business key can
   * then be used to easily look up that process instance , see
   * {@link ProcessInstanceQuery#processInstanceBusinessKey(String)}. Providing such a business
   * key is definitely a best practice.
   *
   * Note that a business key MUST be unique for the given process definition.
   * Process instance from different process definition are allowed to have the
   * same business key.
   *
   * @param processDefinitionId the id of the process definition, cannot be null.
   * @param businessKey a key that uniquely identifies the process instance in the context or the
   *                    given process definition.
   * @param properties the properties to pass, can be null.
   */
  ProcessInstance submitStartForm(String processDefinitionId, String businessKey, Map<String, Object> properties);

  /** Retrieves all data necessary for rendering a form to complete a task.  This can be used to perform rendering of the forms outside of the process engine. */
  TaskFormData getTaskFormData(String taskId);

  /** Rendered form generated by the default build-in form engine for completing a task. */
  Object getRenderedTaskForm(String taskId);

  /** Rendered form generated by the given build-in form engine for completing a task. */
  Object getRenderedTaskForm(String taskId, String formEngineName);

  /**
   * @deprecated use {@link #submitTaskForm(String, Map)} */
  @Deprecated
  void submitTaskFormData(String taskId, Map<String, String> properties);

  /**
   * Completes a task with the user data that was entered as properties in a task form.
   *
   * @param taskId
   * @param properties
   */
  void submitTaskForm(String taskId, Map<String, Object> properties);

  /**
   * Retrieves a list of all variables for rendering a start from. The method takes into account
   * FormData specified for the start event. This allows defining default values and validators for form fields.
   *
   * @param processDefinitionId the id of the process definition for which the start form should be retreived.
   * @return a map of VariableInstances.
   */
  Map<String, VariableInstance> getStartFormVariables(String processDefinitionId);

  /**
   * Retrieves a list of requested variables for rendering a start from. The method takes into account
   * FormData specified for the start event. This allows defining default values and validators for form fields.
   *
   * @param processDefinitionId the id of the process definition for which the start form should be retreived.
   * @param formVariables a Collection of the names of the variables to retrieve.
   * @return a map of VariableInstances.
   */
  Map<String, VariableInstance> getStartFormVariables(String processDefinitionId, Collection<String> formVariables);

  /**
   * Retrieves a list of requested variables for rendering a task form. The method takes into account
   * FormData specified for the task. This allows defining default values and validators for form fields.
   *
   * @param taskId the id of the task for which the variables should be retreived.
   * @return a map of VariableInstances.
   */
  Map<String, VariableInstance> getTaskFormVariables(String taskId);

  /**
   * Retrieves a list of requested variables for rendering a task form. The method takes into account
   * FormData specified for the task. This allows defining default values and validators for form fields.
   *
   * @param taskId the id of the task for which the variables should be retreived.
   * @param formVariables a Collection of the names of the variables to retreive. Allows restricting the set of retreived variables.
   * @return a map of VariableInstances.
   */
  Map<String, VariableInstance> getTaskFormVariables(String taskId, Collection<String> formVariables);

  /**
   * Retrieves a user defined reference to a start form.
   *
   * In the Explorer app, it is assumed that the form key specifies a resource
   * in the deployment, which is the template for the form.  But users are free
   * to use this property differently.
   */
  String getStartFormKey(String processDefinitionId);

  /**
   * Retrieves a user defined reference to a task form.
   *
   * In the Explorer app, it is assumed that the form key specifies a resource
   * in the deployment, which is the template for the form.  But users are free
   * to use this property differently.
   *
   * Both arguments can be obtained from {@link Task} instances returned by any
   * {@link TaskQuery}.
   */
  String getTaskFormKey(String processDefinitionId, String taskDefinitionKey);

}
