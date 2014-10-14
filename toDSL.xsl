<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://kpi.fei.tuke.sk/scyr/nosal/papers">

    <xsl:template match="openconf">
        <papers xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://kpi.fei.tuke.sk/scyr/nosal/papers papers.xsd">
            <xsl:apply-templates select="entry"/>
        </papers>
    </xsl:template>
    
    <xsl:template match="entry">
        <paper>
            <xsl:attribute name="filename"><xsl:value-of select="SubmissionID" />.pdf</xsl:attribute>
            <xsl:if test="Pages">
                <xsl:attribute name="pages"><xsl:value-of select="Pages" /></xsl:attribute>/>
            </xsl:if>
            <authors>
                <xsl:apply-templates select="Author1FirstName"/>
                <xsl:apply-templates select="Author2FirstName"/>
            </authors>
            <title><xsl:value-of select="Title" /></title>
            <presentationForm>NONE</presentationForm>
			<xsl:variable name="section" select="Topics"/>
            <section>
                <xsl:if test="starts-with($section, 'it')">IT</xsl:if>
                <xsl:if test="starts-with($section, 'eee')">EEE</xsl:if>
            </section>
            <department><xsl:value-of select="Author1Department" /></department>
        </paper>
    </xsl:template>
    
    <xsl:template match="Author1FirstName">
        <author>
            <firstName><xsl:value-of select="." /></firstName>
            <surname><xsl:value-of select="../Author1LastName" /></surname>
        </author>
    </xsl:template>
    
    <xsl:template match="Author2FirstName">
        <xsl:if test="string(.)">
            <author>
                <firstName><xsl:value-of select="." /></firstName>
                <surname><xsl:value-of select="../Author2LastName" /></surname>
            </author>
        </xsl:if>        
    </xsl:template>

</xsl:stylesheet>