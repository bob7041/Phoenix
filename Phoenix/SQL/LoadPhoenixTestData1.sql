use phoenixdb;

-- UIMap table
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('NavBar', 'UserOptionsMenu',  'id', 'user-options', 'User Options Menu');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('NavBar', 'CreateIssueButton', 'id', 'create_link', 'Create Issue Button');

INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('Login', 'LoginUserName', 'id', 'login-form-username', 'Login User Name');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('Login', 'LoginPassword', 'id', 'login-form-password', 'Login Password');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('Login', 'LoginButton',   'name', 'login', 'Log in button');

INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('Logout', 'LogoutButtonText', 'text', 'Log Out', 'Log out button');

INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('CreateDialog', 'ConfigureFields', 'id', 'qf-field-picker-trigger', 'Configure Fields Menu');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('CreateDialog', 'Project', 'id', 'project-field', 'Project Name');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('CreateDialog', 'IssueType', 'id', 'issuetype-field', 'Issue Type Menu');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('CreateDialog', 'Summary', 'id', 'summary', 'Summary');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('CreateDialog', 'Description', 'id', 'tinymce', 'Description');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('CreateDialog', 'Environment', 'id', 'tinymce', 'Envinronment');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('CreateDialog', 'Assignee', 'id', 'assignee-field', 'Assignee');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('CreateDialog', 'Priority', 'id', 'priority-field', 'Priority');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('CreateDialog', 'Labels', 'id', 'labels-textarea', 'Labels');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('CreateDialog', 'SubmitButton', 'id', 'create-issue-submit', 'Submit Button');

INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('Dashboard', 'DashPageTitle', 'title', 'System Dashboard - AST JIRA', 'Dashboard Page Title');

INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('Homepage', 'IssuesMenu', 'id', 'find_link', 'Issues Menu');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('Search', 'Currentsearch', 'linkText', 'Current search', 'Issues Menu - Current Search option');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('Search', 'Orderby', 'class', 'order-options', 'Order by menu');
INSERT INTO UIMap (pagename, fieldname, bylocator, field_locator, description) values ('Search', 'Created', 'id', 'order-by-options-input', 'Order by menu selection');

-- TestData table

INSERT INTO TestData
(testset, testcase_descr, action_type, project, issue_type, issue_summary, issue_description, priority, labels, environment, linked_issued, issue, assignee, epic_link, sprint)
VALUES
('testset1', 'basic defect insertion', 'insert', 'Test1', 'defect', 'This is my first bug!', 'This is my first issue description', 'high', '', 'Windows 10', '', '', 'Bob', '', '');

INSERT INTO TestData 
(testset, testcase_descr, action_type, project, issue_type, issue_summary, issue_description, priority, labels, environment, linked_issued, issue, assignee, epic_link, sprint)
VALUES
('testset1', 'basic defect insertion', 'insert', 'Test1', 'defect', 'This is my second bug!', 'This is my second issue description', 'low', '', 'Windows 7', '', '', 'Bob', '', '');



