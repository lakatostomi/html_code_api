This REST API generates random HTML code. The API support the following HTML elements:
`h1`, `p`, `a`, `table`, `tr` and `td`. Only the body of HTML can be edited and each element can handle only
one attribute. Some elements can have child elements like `p`, `table` and `tr`.
Before saving the document the elements can be removed and the final document will be created
based on the order in which the elements were saved.