
foaf_Organization = {
      ":hasContactInfo": "vcard:Organization",
      "foaf:title":"xsd:string",
      "vivo:description":"xsd:string",
      "vivo:identifier":"rdfs:Literal"
    }
 
vivo_University=roh_ResearchInstitute= vivo_Institute= vivo_GovernmentAgency= vivo_Foundation= vivo_AcademicDepartment= roh_Departmen= roh_SmallEnterprise=  vivo_Consortium=vivo_Center=roh_UniversityDivision=roh_EthicsCommitte=roh_ResearchGroup=roh_ManagementUnit= {
        "base": foaf_Organization
    }

vivo_Company= {
        "base": foaf_Organization,
        ":publicCompany":"xsd:boolean"
    }


roh_LargeEnterprise= roh_MediumEnterprise= roh_SmallEnterprise= roh_MicroEnterprise={
        "base": vivo_Company
}


foaf_Person=foaf_Group=foaf_Agent={}


def imprimir(diccionario):
    for key in diccionario:
        dic = diccionario  
        if key=="base":
            dic = dic[key]
            imprimir(dic)
        else:
            print( key, ":", dic[key] )

if __name__ == '__main__':
    imprimir(roh_ResearchInstitute)


