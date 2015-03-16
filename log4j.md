Modyfikacje w log4j

# Dodatkowe wpisy do standardowego log4j #
Do pliku jboss-log4j.xml  dopisujemy
```
<!-- anulowanie bedu EJB3 -->
<category name="org.jboss.ejb3.interceptors">
    <priority value="FATAL" />
</category>

<!-- anulowanie bedu ognl przy przekazywaniu zmiennej  -->
<category name="com.opensymphony.xwork2.ObjectFactory">
    <priority value="FATAL" />
</category>
```