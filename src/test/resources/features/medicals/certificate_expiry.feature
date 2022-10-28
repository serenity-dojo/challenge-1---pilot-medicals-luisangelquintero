Feature: Certificate expiry
  In order to ensure that pilots meet the health condition for their category
  As a FAA supervisor
  I want to have the pilot downgraded to the next lowest if the medical date is not respected

  Rule: A license category is dropped to a less category if the medical date is not respected

    Scenario Outline: Medical certificate expire for First-class pilot
      Given "<Pilot>" is a 1st class pilot <Age> years old
      When there were passed <Months> months after the certificate's expedition
      Then The license should be dropped to the <New Class> category

      Examples:
        | Pilot | Age | Months | New Class |
        | Juan  | 38  | 13     | 3rd class |
        | Maria | 35  | 61     | Expired   |
        | Cesar | 42  | 7      | 2nd class |
        | Mike  | 52  | 13     | 3rd class |
        | Maria | 41  | 25     | Expired   |


    Scenario Outline: Medical certificate expire for Second-class pilot
      Given "<Pilot>" is a 2nd class pilot <Age> years old
      When there were passed <Months> months after the certificate's expedition
      Then The license should be dropped to the <New Class> category

      Examples:
        | Pilot | Age | Months | New Class |
        | Pedro | 38  | 13     | 3rd class |
        | Tere  | 35  | 61     | Expired   |
        | John  | 52  | 13     | 3rd class |
        | David | 41  | 25     | Expired   |


    Scenario Outline: Medical certificate expire for Third-class pilot
      Given "<Pilot>" is a 3rd class pilot <Age> years old
      When there were passed <Months> months after the certificate's expedition
      Then The license should be dropped to the <New Class> category

      Examples:
        | Pilot  | Age | Months | New Class |
        | Manuel | 25  | 61     | Expired   |
        | Derek  | 61  | 25     | Expired   |







