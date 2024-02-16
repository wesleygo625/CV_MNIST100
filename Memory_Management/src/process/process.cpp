/**
 * This file contains implementations for methods in the Process class.
 *
 * You'll need to add code here to make the corresponding tests pass.
 */

#include "process/process.h"
#include <cmath>

using namespace std;


Process* Process::read_from_input(std::istream& in) {
    vector<Page*> ps;
    size_t num = 0;
    while (in.good()) {
        Page* p = Page::read_from_input(in);
        ps.push_back(p);
        num += p->size();
    }
    Process* p = new Process(num, ps);
    return p;
}


size_t Process::size() const
{
    return this->num_bytes;
}


bool Process::is_valid_page(size_t index) const
{
    if (index >= 0 && index < pages.size()) {
        return true;
    }
    return false;
}


size_t Process::get_rss() const
{
    int count = 0;
    for (int i = 0; i < pages.size(); i++) {
        if (page_table.rows[i].present) {
            count++;
        }
    }
    return count;
}


double Process::get_fault_percent() const
{
    if (memory_accesses == 0) {
        return 0.0;
    }
    return std::round((float)page_faults / (float)memory_accesses * 100);
}
