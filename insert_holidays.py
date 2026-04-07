import pandas as pd
from sqlalchemy import create_engine

# Database Connection Details
# Replace with your actual PostgreSQL connection string.
# NOTE: If your Spring Boot app uses an H2 in-memory DB (jdbc:h2:mem:...), 
# a python script cannot connect to it directly unless the H2 server is exposed.
# If you switch Spring Boot to use PostgreSQL, this script will work perfectly using psycopg2.
# Format: postgresql+psycopg2://username:password@host:port/database_name
DB_URL = "postgresql+psycopg2://postgres:postgres@localhost:5432/testdb"

CSV_FILE = "holidays.csv"

def insert_holidays():
    print(f"Reading {CSV_FILE}...")
    try:
        df = pd.read_csv(CSV_FILE)
    except FileNotFoundError:
        print(f"Error: {CSV_FILE} not found.")
        return

    # Rename CSV columns to match the holiday table columns in DB
    # The 'holidays' table has: id (auto-generated), holiday_date, holiday_name
    df.rename(columns={'date': 'holiday_date', 'name': 'holiday_name'}, inplace=True)

    # Convert holiday_date to date objects (optional but recommended for reliability)
    df['holiday_date'] = pd.to_datetime(df['holiday_date']).dt.date

    print(f"Connecting to database using SQLAlchemy...")
    try:
        engine = create_engine(DB_URL)
        
        print("Inserting records into 'holidays' table...")
        # Insert records. if_exists='append' ensures we add to the existing table
        # index=False ensures the DataFrame index isn't written as a DB column
        df.to_sql('holidays', engine, if_exists='append', index=False)
        print("Insertion successful!")
    except Exception as e:
        print(f"Database insertion failed: {e}")

if __name__ == "__main__":
    insert_holidays()
