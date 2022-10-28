Feature: Date of next medical
  In order to ensure that pilots are in good health conditions
  As a FAA supervisor
  I want to verify how often the medical certificate expires for pilots

  Rule: Medical certificates for each class have an expiration date

    Scenario Outline: Medical certificates for First-class Pilots expire  every 12 months or every 6 months if the pilot is older than 40 years
      Given "Juan" is a 1st class pilot <Age> years old
      When They got his certificate on the <Expedition Date>
      Then  their medical certificate should be valid until <Expiration Date>

      Examples:
        | Age | Expedition Date | Expiration Date |
        | 39  | 01-JAN-2022     | 31-JAN-2023     |
        | 42  | 01-JAN-2022     | 31-JUL-2022     |

    Scenario Outline: Medical certificates for Second-class Pilots expire  every 12 months
      Given "Maria" is a 2nd class pilot <Age> years old
      When They got his certificate on the <Expedition Date>
      Then  their medical certificate should be valid until <Expiration Date>

      Examples:
        | Age | Expedition Date | Expiration Date |
        | 39  | 01-JAN-2022     | 31-JAN-2023     |
        | 42  | 01-JAN-2022     | 31-JAN-2023     |

    Scenario Outline: Medical certificates for Third-class Pilots expire  every 5 years or every 2 years if the pilot is older than 40 years
      Given "Pedro" is a 3rd class pilot <Age> years old
      When They got his certificate on the <Expedition Date>
      Then  their medical certificate should be valid until <Expiration Date>

      Examples:
        | Age | Expedition Date | Expiration Date |
        | 39  | 01-JAN-2022     | 31-JAN-2027     |
        | 42  | 01-JAN-2022     | 31-JAN-2024     |



  Rule: Medical certificates duration is measured from the last day of the month

    Scenario Outline: Pilots check how long have their certification
      Given "Juan" is a pilot with a medical certificate since <Expedition Date>
      When they check how old is their Medical Certificate on the day of <Current Date>
      Then their certificate should have <Months> months old

      Examples:
        | Expedition Date | Current Date | Months |
        | 07-JUN-2022     | 07-FEB-2023  | 8      |
        | 01-JAN-2022     | 01-FEB-2022  | 1      |
        | 03-JAN-2022     | 24-JAN-2022  | 0      |