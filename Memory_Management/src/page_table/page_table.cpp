/**
 * This file contains implementations for methods in the PageTable class.
 *
 * You'll need to add code here to make the corresponding tests pass.
 */

#include "page_table/page_table.h"

using namespace std;


size_t PageTable::get_present_page_count() const {
    int count = 0;
    for(int i = 0; i < rows.size(); i++) {
        if (rows.at(i).present) {
            count++;
        }
    }
    return count;
}


size_t PageTable::get_oldest_page() const {
    vector<int> actives;
    for (int i = 0; i < rows.size(); i++) {
        if (rows.at(i).present) {
            actives.push_back(i);
        }
    }
    size_t min = actives[0];
    size_t min_time = rows.at(actives[0]).loaded_at;

    for (int i = 0; i < actives.size(); i++) {
        if (rows.at(actives[i]).loaded_at < min_time && rows.at(actives[i]).loaded_at >= 0) {
            min = actives[i];
            min_time = rows.at(actives[i]).loaded_at;
        }
    }
    return min;
}


size_t PageTable::get_least_recently_used_page() const {
    vector<int> actives;
    for (int i = 0; i < rows.size(); i++) {
        if (rows.at(i).present) {
            actives.push_back(i);
        }
    }
    size_t min = actives[0];
    size_t min_time = rows.at(actives[0]).last_accessed_at;

    for (int i = 0; i < actives.size(); i++) {
        if (rows.at(actives[i]).last_accessed_at < min_time && rows.at(actives[i]).last_accessed_at >= 0) {
            min = actives[i];
            min_time = rows.at(actives[i]).last_accessed_at;
        }
    }
    return min;
    return 0;
}
